import { Routes } from '@angular/router';
import { Name } from './core/auth/name/name';
import { Homepage } from './components/homepage/homepage';
import { Analyzer } from './components/analyzer/analyzer';

export const routes: Routes = [
  {
    path: "",
    component: Homepage,
    title: "Video Analyzer"
  },
  {
    path: "name",
    component: Name,
    title: "Nos diga quem você é"
  },
  {
    path: "analyzer",
    component: Analyzer,
    title: "Analisador de videos"
  }
];
