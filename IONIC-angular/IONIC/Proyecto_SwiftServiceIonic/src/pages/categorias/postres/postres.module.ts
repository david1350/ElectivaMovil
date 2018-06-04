import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { PostresPage } from './postres';

@NgModule({
  declarations: [
    PostresPage,
  ],
  imports: [
    IonicPageModule.forChild(PostresPage),
  ],
})
export class PostresPageModule {}
