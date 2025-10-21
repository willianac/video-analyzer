import { Component, inject, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { Summary } from '../../core/types/summary';
import { NzButtonModule } from 'ng-zorro-antd/button';

@Component({
  selector: 'app-revisit-summary',
  imports: [NzButtonModule, RouterLink],
  templateUrl: './revisit-summary.html',
  styleUrl: './revisit-summary.css'
})
export class RevisitSummary {
  router = inject(Router);
  summary: Summary | null = null;

  constructor() {
    const navigation = this.router.currentNavigation();
    if (navigation?.extras.state) {
      const state = navigation.extras.state as { summary: Summary };
      this.summary = state.summary;
      console.log(this.summary)
    }
  }
}
