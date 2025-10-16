import { trigger, transition } from '@angular/animations';
import { Component, inject } from '@angular/core';
import { fadeIn } from '@ngverse/motion/animatecss';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzInputModule } from 'ng-zorro-antd/input';
import { Summary } from '../../services/summary';
import { FormsModule } from '@angular/forms';
import { NzAlertModule } from 'ng-zorro-antd/alert';
import { Session } from '../../services/session';

@Component({
  selector: 'app-analyzer',
  imports: [NzInputModule, NzButtonModule, FormsModule, NzAlertModule],
  templateUrl: './analyzer.html',
  styleUrl: './analyzer.css',
  animations: [
    trigger("show", [
      transition(":enter", fadeIn())
    ])
  ]
})
export class Analyzer {
  summaryService = inject(Summary);
  sessionService = inject(Session);
  currentUser = this.sessionService.getCurrentUser();
  loading = false;
  videoId = "";
  summary = "";
  summaryRequestErr = "";
  showBlankInputErr = false;

  public getSummary() {
    if(!this.videoId) {
      return this.showBlankInputErr = true;
    }
    this.loading = true
    this.showBlankInputErr = false;
    
    return this.summaryService.getSummary(this.videoId, this.currentUser.id).subscribe({
      next: (res) => {
        console.log(res)
        this.summary = res
        this.loading = false;
      },
      error: (err) => {
        this.loading = false;
        this.handleErrors(err)
      }
    })
  }

  private handleErrors(err: any) {
    const errMessage = err.error as string;
    if(err.status === 0) {
      return this.summaryRequestErr = "Serviço indisponível no momento, por favor tente novamente mais tarde."
    }
    if(errMessage.startsWith("Error: Video is too long")) {
      return this.summaryRequestErr = "Vídeo muito grande. É permitido usar vídeos de até 1 minuto (60 segundos)"
    }
    if(errMessage.startsWith("Error: Failed to download video.")) {
      return this.summaryRequestErr = "Não foi possível usar este vídeo. Pode ser que o ID esteja errado ou que esteja indisponivel para baixar."
    }

    console.log(err.error)
    return null
  }
}
