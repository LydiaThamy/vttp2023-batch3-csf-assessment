import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { Post } from '../model/Post';

@Injectable({
  providedIn: 'root'
})
export class NewsService {

  constructor(private http: HttpClient) { }

  postNews(post: Post): Observable<any> {

    let form: FormData = new FormData()
    form.append('title', post.title)
    form.append('description', post.description)
    form.append('photo', post.image)
    form.append('tags', post.tags.toString())

    const header: HttpHeaders = new HttpHeaders()
      .set('Content-Type', 'multipart/form-data; boundary=----0YsU72sGdwPe5B')

    console.log(form)
    return this.http.post<any>("/api/news/post", form, {headers: header})
  }

}
