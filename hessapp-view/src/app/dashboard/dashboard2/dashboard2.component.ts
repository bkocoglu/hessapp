import { Component, OnInit} from '@angular/core';
import { ChartType, ChartEvent } from "ng-chartist/dist/chartist.component";
import {ApiClientService} from "../../services/api-client.service";
import {LoginUserInfoService} from "../../services/login-user-info.service";
import {ToastrService} from "../../services/toastr.service";
import {GeneralStatus} from "../../dto/GeneralStatus";
import {barChartLabels} from "../../shared/data/chartjs";

declare var require: any;

export interface Chart {
    type: ChartType;
    data: Chartist.IChartistData;
    options?: any;
    responsiveOptions?: any;
    events?: ChartEvent;
}

export interface Data {
  data: number[];
  label: string;
}

@Component({
    selector: 'app-dashboard2',
    templateUrl: './dashboard2.component.html',
    styleUrls: ['./dashboard2.component.scss']
})

export class Dashboard2Component implements OnInit  {


  private statusList: GeneralStatus[] = [];
  private alacakData: Data = {data: [], label: 'Alacak'};
  private verecekData: Data = {data: [], label: 'Verecek'};
  private chartLabels: string[] = [];

  loading= false;

  public barChartOptions: any = {
    scaleShowVerticalLines: false,
    responsive: true,
    maintainAspectRatio: false

  };
  public barChartLabels = [];
  public barChartType = 'bar';
  public barChartLegend = true;

  public barChartData: any[] = [
  ];

  ngOnInit(): void {
    // console.log('dashboard2 run');
    this.apiClient.generalStatusClient(LoginUserInfoService.nickname).subscribe(
      response => {
        this.statusList = response.list;
        console.log(response.list);


        this.statusList.forEach((status) => {
          this.barChartLabels.push(status.groupName);
          this.alacakData.data.push(status.totalCredit);
          this.verecekData.data.push(status.totalDebt);
        });
        this.alacakData.data.push(0);
        this.verecekData.data.push(0);

        this.barChartData.push(this.alacakData);
        this.barChartData.push(this.verecekData);

        this.loading = true;

      },
      error2 => {
        console.log(error2);
        this.toastr.typeError("Status Chart Error", "Güncel durumunuz listelenemedi !");
      }
    )
  }

  constructor(private apiClient: ApiClientService,
              private toastr: ToastrService) { }

    // Line chart configuration Starts


    public barChartColors: Array<any> = [
        {

          backgroundColor: '#843cf7',
          borderColor: '#843cf7',
          pointBackgroundColor: '#843cf7',
          pointBorderColor: '#fff',
          pointHoverBackgroundColor: '#fff',
          pointHoverBorderColor: '#843cf7'
        },
        {

          backgroundColor: '#38b8f2',
          borderColor: '#38b8f2',
          pointBackgroundColor: '#38b8f2',
          pointBorderColor: '#fff',
          pointHoverBackgroundColor: '#fff',
          pointHoverBorderColor: '#38b8f2'
        },

      ];
    public chartClicked(e: any): void {
        //your code here
      }

      public chartHovered(e: any): void {
        //your code here
      }
    // Line chart configuration Ends
}
