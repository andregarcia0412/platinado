import { Component } from '@angular/core';
import { OutlinedButton } from '../../shared/components/outlined-button/outlined-button';
import { Header } from '../../shared/components/header/header';

@Component({
  imports: [OutlinedButton, Header],
  selector: 'app-my-games',
  templateUrl: './my-games.html',
})
export class MyGames {}
