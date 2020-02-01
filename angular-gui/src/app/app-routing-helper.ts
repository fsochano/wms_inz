import { Authority } from './auth/auth.model';

export function getRedirectPath(authorities: Authority[]) {
  const authSet = new Set<Authority>(authorities);
  if (authSet.has('ORDERING')) {
    return '/order';
  }
  if (authSet.has('PICKING')) {
    return '/picking';
  }
  if (authSet.has('SHIPPING')) {
    return '/shipping';
  }
  if (authSet.has('SETTINGS')) {
    return '/settings';
  }
  return undefined;
}
