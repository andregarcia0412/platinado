import { Component } from '@angular/core';
import { LoginForm } from './components/login-form/login-form';

@Component({
  imports: [LoginForm],
  selector: 'app-auth',
  templateUrl: './auth.html',
})
export class Auth {}
