import { Routes } from '@angular/router';
import { Name } from './core/auth/name/name';
import { Homepage } from './components/homepage/homepage';
import { Analyzer } from './components/analyzer/analyzer';
import { RevisitSummary } from './components/revisit-summary/revisit-summary';

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
  },
  {
    path: "summary",
    component: RevisitSummary,
    title: "Resumo do vídeo"
  }
];
