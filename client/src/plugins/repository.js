export default async (ctx, inject) => {
  const repositoriesConfig = [
    'account',
    'authorization',
    'notification'
  ].reduce((acc, name) => {
    acc[name.toLowerCase().replace(/-(.)/g, (match, group) => group.toUpperCase())] = name;
    return acc;
  }, {});

  const repositories = {};
  for (let key in repositoriesConfig) {
    repositories[key] = await import('~/repository/'+repositoriesConfig[key]);
  }

  inject('repository', Object.entries(repositories)
      .reduce((acc, [key, value]) => { acc[key] = value.default(ctx.$axios); return acc; }, {})
  );
};
