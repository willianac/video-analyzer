import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { trigger, transition } from "@angular/animations";
import { fadeIn } from "@ngverse/motion/animatecss"

@Component({
  selector: 'app-homepage',
  imports: [NzButtonModule, NzIconModule, RouterLink],
  templateUrl: './homepage.html',
  styleUrl: './homepage.css',
  animations: [
    trigger("show", [
      transition(":enter", fadeIn())
    ])
  ]
})
export class Homepage {

}
