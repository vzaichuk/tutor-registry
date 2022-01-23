import {ROLE} from '~/utils/constants';

const ROLE_SELECT_URL = '/account/role';
const INDEX_URL = '/';
const ADMIN_PREFIX_URL = '/admin';
const ADMIN_INDEX_URL = ADMIN_PREFIX_URL + '/applications';

const IGNORED_PATHS = [ROLE_SELECT_URL, INDEX_URL];
const ADMIN_IGNORED_PREFIX = [ADMIN_PREFIX_URL, '/notifications'];

export default function (ctx) {
  if (ctx.$auth.loggedIn && ctx.$auth.user.role == ROLE.ADMIN && ctx.route.path != INDEX_URL && ADMIN_IGNORED_PREFIX.every(v => !ctx.route.path.startsWith(v))) {
    return ctx.redirect(ADMIN_INDEX_URL);
  }

  const roleIsChosen = ctx.store.getters['getField']('auth.roleChosen');
  if (!roleIsChosen && ctx.$auth.loggedIn && !ctx.$auth.user.role && IGNORED_PATHS.indexOf(ctx.route.path) == -1) {
    return ctx.redirect(ROLE_SELECT_URL);
  }
};
