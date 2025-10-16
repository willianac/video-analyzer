import { Injectable } from "@angular/core";
import { User } from "./auth";

@Injectable({
    providedIn: 'root'
})
export class Session {
    public set(key: string, value: string) {
        localStorage.setItem(key, value);

    }

    public get(key: string): string | null {
        return localStorage.getItem(key);
    }

    public remove(key: string) {
        localStorage.removeItem(key);
    }

    public clear() {
        localStorage.clear();
    } 

    public getCurrentUser(): User {
        return JSON.parse(this.get("user") || "{}");
    }

    public getLastVideoSummarized(): string {
        return this.get("lastVideoSummarized") || "";
    }
}