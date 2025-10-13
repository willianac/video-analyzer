import { trigger, transition } from '@angular/animations';
import { Component, inject } from '@angular/core';
import { fadeIn } from '@ngverse/motion/animatecss';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzInputModule } from 'ng-zorro-antd/input';
import { Summary } from '../../services/summary';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-analyzer',
  imports: [NzInputModule, NzButtonModule, FormsModule],
  templateUrl: './analyzer.html',
  styleUrl: './analyzer.css',
  animations: [
    trigger("show", [
      transition(":enter", fadeIn())
    ])
  ]
})
export class Analyzer {
  summaryService = inject(Summary)
  loading = false
  videoId = ""
  summary = ""

  wait() {
    this.loading = true
    
    this.summaryService.getSummary(this.videoId).subscribe({
      next: (res) => {
        console.log(res)
        this.summary = res
      },
      error: (err) => {
        console.log(err)
      }
    })
  }
}
