import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, timeout } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class Summary {
  http = inject(HttpClient)
  url = "http://localhost:8080/video-info/summary?videoId="

  public getSummary(videoId: string): Observable<string> {
    return this.http.get(this.url + videoId, { responseType: "text" }).pipe(
      timeout(50000)
    )
  }
}
