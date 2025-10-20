import { trigger, transition } from '@angular/animations';
import { Component, inject } from '@angular/core';
import { fadeIn } from '@ngverse/motion/animatecss';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzInputModule } from 'ng-zorro-antd/input';
import { Summary } from '../../services/summary';
import { FormsModule } from '@angular/forms';
import { NzAlertModule } from 'ng-zorro-antd/alert';
import { Session } from '../../services/session';
import { RouterLink } from '@angular/router';

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
  summaryService = inject(Summary);
  sessionService = inject(Session);
  currentUser = this.sessionService.getCurrentUser();
  loading = false;
  videoString = "";
  summary = "";

  summaryRequestErr = "";
  sameSummaryRequestErr = "";
  invalidVideoIdErr = "";

  showBlankInputErr = false;

  public getSummary() {
    this.showBlankInputErr = false;
    this.sameSummaryRequestErr = "";
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

    if(this.sessionService.get("lastVideoSummarized") === videoId) {
      this.loading = false;
      return this.sameSummaryRequestErr = "Você já pediu o resumo desse vídeo. Por favor, insira outro vídeo."
    }

    this.loading = true

    return this.summaryService.getSummary(videoId, this.currentUser.id).subscribe({
      next: (res) => {
        this.summary = res
        this.loading = false;
        this.sessionService.set("lastVideoSummarized", this.videoString);
      },
      error: (err) => {
        this.loading = false;
        this.handleErrors(err)
      }
    })
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
