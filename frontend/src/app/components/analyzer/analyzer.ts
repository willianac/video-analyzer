import { trigger, transition } from '@angular/animations';
import { Component, inject } from '@angular/core';
import { fadeIn } from '@ngverse/motion/animatecss';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzInputModule } from 'ng-zorro-antd/input';
import { SummaryService } from '../../services/summary';
import { FormsModule } from '@angular/forms';
import { NzAlertModule } from 'ng-zorro-antd/alert';
import { Session } from '../../services/session';
import { Router, RouterLink } from '@angular/router';
import { EMPTY, mergeMap } from 'rxjs';
import { Summary } from '../../core/types/summary';

@Component({
  selector: 'app-analyzer',
  imports: [NzInputModule, NzButtonModule, FormsModule, NzAlertModule, RouterLink],
  templateUrl: './analyzer.html',
  styleUrl: './analyzer.css',
  animations: [
    trigger("show", [
      transition(":enter", fadeIn())
    ])
  ]
})
export class Analyzer {
  summaryService = inject(SummaryService);
  sessionService = inject(Session);
  router = inject(Router);
  currentUser = this.sessionService.getCurrentUser();
  loading = false;
  videoString = "";
  summary = "";

  summaryRequestErr = "";
  invalidVideoIdErr = "";
  
  showSameSummaryRequestErr = false;
  showBlankInputErr = false;
  summaryToRevisit: Summary | null = null;

  public getSummary() {
    this.showBlankInputErr = false;
    this.showSameSummaryRequestErr = false;
    this.invalidVideoIdErr = "";
    this.summaryRequestErr = "";

    const videoId = this.testAndReturnVideoId(this.videoString);

    if(!this.videoString) {
      return this.showBlankInputErr = true;
    }

    if(!videoId) {
      this.loading = false;
      return this.invalidVideoIdErr = "ID/URL de vídeo inválido. Por favor, insira um ID ou URL de vídeo do YouTube válido."
    }

    this.loading = true

    return this.summaryService.getUserSummaries(this.currentUser.id).pipe(
      mergeMap(res => {
        const hasSummaryAlready = res.find((summary) => summary.videoId === videoId);
        if(hasSummaryAlready) {
          this.loading = false;
          this.summaryToRevisit = hasSummaryAlready;
          this.showSameSummaryRequestErr = true;
          return EMPTY
        }
        return this.summaryService.getSummary(videoId, this.currentUser.id);
      })

    ).subscribe({
      next: (res) => {
        this.summary = res;
        this.loading = false;
      },
      error: (err) => {
        this.loading = false;
        this.handleErrors(err);
      }
    })
  }

  public revisitSummary() {
    if(this.summaryToRevisit)  {
      this.router.navigate(["/summary"], { state: { summary: this.summaryToRevisit } });
    }
  }

  private testAndReturnVideoId(videoString: string) {
    const videoIdRegex = /^(?:https?:\/\/)?(?:www\.)?(?:youtube\.com\/watch\?v=)?([a-zA-Z0-9_-]{11})$/;
    const match = videoString.match(videoIdRegex);
    if(match) {
      return match[1];
    }
    return false;
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
