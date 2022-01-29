import {ROLE} from '~/utils/constants';

export const isAdmin = user => user.role == ROLE.ADMIN;

export const isTutor = user => user.role == ROLE.TUTOR;

export const isStudent = user => user.role == ROLE.STUDENT;
