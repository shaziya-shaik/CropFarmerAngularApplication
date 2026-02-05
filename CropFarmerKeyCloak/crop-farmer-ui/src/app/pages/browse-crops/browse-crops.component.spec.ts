import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BrowseCropsComponent } from './browse-crops.component';

describe('BrowseCropsComponent', () => {
  let component: BrowseCropsComponent;
  let fixture: ComponentFixture<BrowseCropsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BrowseCropsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BrowseCropsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
