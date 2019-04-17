import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TfaQrComponent } from './tfa-qr.component';

describe('TfaQrComponent', () => {
  let component: TfaQrComponent;
  let fixture: ComponentFixture<TfaQrComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TfaQrComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TfaQrComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
