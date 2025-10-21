import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable, tap } from "rxjs";

export type User = {
    id: number;
    name: string;
}

@Injectable({
    providedIn: "root"
})
export class Auth {
    private httpClient = inject(HttpClient);
    private url = import.meta.env.NG_APP_API_URL as string;

    public signIn(name: string): Observable<User> {
        return this.httpClient.post<User>(this.url + "/users/signin", { name }).pipe(
            tap((res) => {console.log(res)})
        )
    }
}