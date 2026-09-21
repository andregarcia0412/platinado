import { Injectable, Type } from '@angular/core';
import { LoginForm } from '../components/login-form/login-form';
import { RegisterForm } from '../components/register-form/register-form';
import { ForgotPasswordForm } from '../components/forgot-password-form/forgot-password-form';
import { AuthFormType } from './auth-navigation';

@Injectable({ providedIn: 'root' })
export class AuthFormFactory {
  makeAuthForm(form: AuthFormType): Type<unknown> {
    switch (form) {
      case 'login':
        return LoginForm;
      case 'register':
        return RegisterForm;
      case 'forgot-password':
        return ForgotPasswordForm;
    }
  }
}
