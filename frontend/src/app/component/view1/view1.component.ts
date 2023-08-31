import { ActivatedRoute, Router } from '@angular/router';
import { NewsService } from 'src/app/service/news.service';
import { Component, OnInit } from '@angular/core';
import { Post } from 'src/app/model/Post';

@Component({
  selector: 'app-view1',
  templateUrl: './view1.component.html',
  styleUrls: ['./view1.component.css']
})
export class View1Component {

  constructor(private service: NewsService, private router: Router, private aRoute: ActivatedRoute) { }
  tag: string = this.aRoute.snapshot.params['tag']
  newsList: Post[] = []

  ngOnInit() {

  }

  getNews(tag: string) {
    this.service.getNews(tag)
      .subscribe(data => {
        data.forEach((e: any) => {
          const post: Post = {
            id: '',
            postDate: e['postDate'] as number,
            title: e['title'],
            description: e['description'],
            image: e['image'],
            tags: e['tags'] as string[]
          }
          this.newsList.push(post)
        });
      })
  }
}
