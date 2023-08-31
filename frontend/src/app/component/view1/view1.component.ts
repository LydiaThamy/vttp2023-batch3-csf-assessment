import { ActivatedRoute, Router } from '@angular/router';
import { NewsService } from 'src/app/service/news.service';
import { Component, OnInit } from '@angular/core';
import { Post } from 'src/app/model/Post';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-view1',
  templateUrl: './view1.component.html',
  styleUrls: ['./view1.component.css']
})
export class View1Component {

  constructor(private service: NewsService, private router: Router, private aRoute: ActivatedRoute) { }
  tag: string = this.aRoute.snapshot.params['tag']
  newsList: Post[] = []
  time!: number
  sub$!: Subscription

  ngOnInit() {
    const storedTime = sessionStorage.getItem("time")
    if (storedTime == null) {
      this.time = 5
    } else {
      this.time = Number.parseInt(storedTime)
    }

  this.getNews()
  }

  getNews() {
    this.sub$ = this.service.getNews(this.tag, this.time)
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
          console.log(JSON.stringify(post))
          this.newsList.push(post)
        });
      })
  }

  ngOnDestroy() {
    this.sub$.unsubscribe()
  }
}
