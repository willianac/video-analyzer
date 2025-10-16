import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";

@Injectable({
    providedIn: "root"
})
export class Auth {
    private httpClient = inject(HttpClient);
    private url = "http://localhost:8080";

    public signIn(name: string) {
        return this.httpClient.post(this.url + "/users/signin", { name })
    }
}