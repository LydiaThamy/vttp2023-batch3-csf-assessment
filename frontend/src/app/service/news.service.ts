import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { Post } from '../model/Post';
import { TagCount } from '../model/TagCount';

@Injectable({
  providedIn: 'root'
})
export class NewsService {

  constructor(private http: HttpClient) { }

  postNews(post: Post): Observable<any> {

    let form: FormData = new FormData()
    form.set('title', post.title)
    form.set('description', post.description)
    form.set('image', post.image)

    if (post.tags.length > 0)
      form.set('tags', post.tags.toString())

    console.log(JSON.stringify(form))

    return this.http.post<any>("/api/news/post", form)
  }

  getTags(time: number) {
    const params = new HttpParams()
      .set("time", time)
    return this.http.get<any>("/api/news/tags", {params: params})
  }

  getNews(tag: string) {
    return this.http.get<any>("/api/news/" + tag)
  }

}
