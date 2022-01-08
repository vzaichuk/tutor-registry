const ROLE_SELECT_URL = '/account/role';

const IGNORED_PATHS = [ROLE_SELECT_URL, '/'];

export default function (ctx) {
  const roleIsChosen = ctx.store.getters['getField']('auth.roleChosen');
  if (!roleIsChosen && ctx.$auth.loggedIn && !ctx.$auth.user.role && IGNORED_PATHS.indexOf(ctx.route.path) == -1) {
    return ctx.redirect(ROLE_SELECT_URL);
  }
};
