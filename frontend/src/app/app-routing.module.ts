import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { View0Component } from './component/view0/view0.component';
import { View1Component } from './component/view1/view1.component';
import { View2Component } from './component/view2/view2.component';

const routes: Routes = [
  {path: '', component: View0Component},
  {path: 'news/:hashtag', component: View1Component},
  {path: 'post', component: View2Component},
  {path: "**", redirectTo: '/', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
