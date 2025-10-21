import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, timeout } from 'rxjs';
import { Summary } from '../core/types/summary';

@Injectable({
  providedIn: 'root'
})
export class SummaryService {
  private http = inject(HttpClient)
  private url = import.meta.env.NG_APP_API_URL as string;

  public getSummary(videoId: string, userId: number): Observable<string> {
    const url = `${this.url}/video-info/summary?videoId=${videoId}&userId=${userId}`;
    return this.http.get(url, { responseType: "text" }).pipe(
      timeout(50000)
    )
  }

  public getUserSummaries(userId: number): Observable<Summary[]> {
    const url = `${this.url}/users/summaries?userId=${userId}`;
    return this.http.get<Summary[]>(url);
  }
}
