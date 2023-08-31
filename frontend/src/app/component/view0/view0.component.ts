import { NewsService } from 'src/app/service/news.service';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { TagCount } from 'src/app/model/TagCount';

@Component({
  selector: 'app-view0',
  templateUrl: './view0.component.html',
  styleUrls: ['./view0.component.css']
})
export class View0Component {

  timeForm!: FormGroup
  time!: number
  tagCountList: TagCount[] = []
  constructor(private fb: FormBuilder, private service: NewsService) {}

  ngOnInit(): void {
    // set time
    const savedTime = sessionStorage.getItem("time")
    if (savedTime != null) {
      this.time = Number.parseInt(savedTime)
    } else {
      this.time = 5
    }

    // create form
    this.createForm(this.time)

    // get tags
    this.getTags(this.time)
  }

  createForm(time: number) {
    this.timeForm = this.fb.group({
      time: this.fb.control<number>(time)
    })
  }

  changeTime() {
    this.time = Number.parseInt(this.timeForm.value['time'])
    this.getTags(this.time)

    sessionStorage.setItem("time", this.time.toString())
  }

  getTags(time: number) {
    this.service.getTags(time).subscribe(data =>
      {
        data.forEach((e: any) => {
          const tc: TagCount = {
            tag: JSON.parse(e)["tag"],
            count: JSON.parse(e)["count"] as number
          }
          this.tagCountList.push(tc)
        });
      }
    )
  }
  
}
