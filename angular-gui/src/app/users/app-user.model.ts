export interface AppUser {
  username: string;
  authorities: string[];
}

export interface AppUserParams {
  username: string;
  password: string;
}
