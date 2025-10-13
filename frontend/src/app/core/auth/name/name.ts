import { trigger, transition } from '@angular/animations';
import { Component } from '@angular/core';
import { fadeIn } from '@ngverse/motion/animatecss';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzInputModule } from 'ng-zorro-antd/input';

@Component({
  selector: 'app-name',
  imports: [NzInputModule, NzButtonModule],
  templateUrl: './name.html',
  styleUrl: './name.css',
   animations: [
    trigger("show", [
      transition(":enter", fadeIn())
    ])
  ]
})
export class Name {

}
