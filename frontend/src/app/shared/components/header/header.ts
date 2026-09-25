import { NgClass } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { Avatar } from '../avatar/avatar';

@Component({
  imports: [RouterLink, RouterLinkActive, NgClass, Avatar],
  selector: 'app-header',
  templateUrl: './header.html',
})
export class Header {}
