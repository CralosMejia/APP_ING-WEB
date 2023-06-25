import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PagesModule } from './pages/pages.module';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { AuthModule } from '@auth0/auth0-angular';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    PagesModule,
    HttpClientModule,


    // Import the module into the application, with configuration
    AuthModule.forRoot({
      domain: 'dev-h63ixdlnx12jef4m.us.auth0.com',
      clientId: 'wHF3psYES1R9kwemoDqrtQezc18HFPvD',
      authorizationParams: {
        redirect_uri: window.location.origin
      }
    }),

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
