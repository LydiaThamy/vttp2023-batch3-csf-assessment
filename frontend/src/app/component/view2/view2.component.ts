import { HttpErrorResponse } from '@angular/common/http';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Post } from 'src/app/model/Post';
import { NewsService } from 'src/app/service/news.service';

@Component({
  selector: 'app-view2',
  templateUrl: './view2.component.html',
  styleUrls: ['./view2.component.css']
})
export class View2Component implements OnInit {

  newsForm!: FormGroup
  @ViewChild('photo') photo!: ElementRef
  tagArr: string[] = []

  constructor(private fb: FormBuilder, private router: Router, private service: NewsService) { }

  ngOnInit(): void {
    this.createForm()
  }

  createForm(): void {
    this.newsForm = this.fb.group({
      title: this.fb.control<string>('', [Validators.required, Validators.minLength(5)]),
      description: this.fb.control<string>('', [Validators.required, Validators.minLength(5)]),
      tags: this.fb.control<string>('')
    })
  }

  addTags(): void {
    // process string and split into array
    this.addTagsToArr()

    // reset tags
    this.newsForm.controls['tags'].reset()
  }

  addTagsToArr(): void {
    const tags: string = this.newsForm.value['tags'].trim()

    for (let s of tags.split(" ")) {
      if (this.tagArr.includes(s) == false) {
        this.tagArr.push(s)
      }
    }
  }

  deleteTag(tag: string): void {
    // remove tag from arr
    const index: number = this.tagArr.indexOf(tag)
    this.tagArr.splice(index, 1)
  }

  postNews(): void {
    // push remaining tags to array
    this.addTagsToArr()

    const post: Post = {
      id: '',
      postDate: 0,
      title: this.newsForm.value['title'],
      description: this.newsForm.value['description'],
      image: this.photo.nativeElement.files[0],
      tags: this.tagArr
    }

    console.log(JSON.stringify(post))

    // post to api
    this.service.postNews(post)
      .subscribe({
        next: data => {
          alert("News ID " + data + " has been created!")

          // navigate to view0 in subscription
          this.router.navigate(['/'])
        },
        
        error: (error: HttpErrorResponse) => {
          alert(error['error']['message'])
        }
      })



  }

}
