import { trigger, transition } from '@angular/animations';
import { Component } from '@angular/core';
import { fadeIn } from '@ngverse/motion/animatecss';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzInputModule } from 'ng-zorro-antd/input';

@Component({
  selector: 'app-analyzer',
  imports: [NzInputModule, NzButtonModule],
  templateUrl: './analyzer.html',
  styleUrl: './analyzer.css',
  animations: [
    trigger("show", [
      transition(":enter", fadeIn())
    ])
  ]
})
export class Analyzer {
  loading = false
  summary = ""

  wait() {
    this.loading = true
    
    setTimeout(() => {
      this.summary = "ss"
      this.loading = false
    }, 2000)
  }
}
