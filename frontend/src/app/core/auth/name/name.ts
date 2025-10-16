import { trigger, transition } from '@angular/animations';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { fadeIn } from '@ngverse/motion/animatecss';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzInputModule } from 'ng-zorro-antd/input';
import { Auth } from '../../../services/auth';

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
  router = inject(Router);
  authService = inject(Auth);
  name = "";
  blankNameError = false;
  authError = false;
  loading = false;

  public continue() {
    if(!this.name) return this.blankNameError = true

    this.loading = true;

    return this.authService.signIn(this.name).subscribe({
      next: () => this.proceed(),
      error: (err) => this.handleError(err),
      complete: () => this.loading = false
    })
  }

  private handleError(err: any) {
    this.authError = true;
    this.loading = false;
    console.error(err);
  }

  private proceed() {
    this.router.navigateByUrl("/analyzer");
  }
}
