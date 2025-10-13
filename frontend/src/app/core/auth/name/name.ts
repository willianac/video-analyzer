import { trigger, transition } from '@angular/animations';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { fadeIn } from '@ngverse/motion/animatecss';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzInputModule } from 'ng-zorro-antd/input';

@Component({
  selector: 'app-name',
  imports: [NzInputModule, NzButtonModule, FormsModule],
  templateUrl: './name.html',
  styleUrl: './name.css',
  animations: [
    trigger("show", [
      transition(":enter", fadeIn())
    ])
  ]
})
export class Name {
  router = inject(Router)
  name = "";
  blankNameError = false;

  public continue() {
    if(!this.name) return this.blankNameError = true
    return this.router.navigateByUrl("/analyzer")
  }
}
