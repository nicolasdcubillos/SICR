import { Injectable } from "@angular/core";
import { HttpInterceptor, HttpRequest, HttpHandler } from "@angular/common/http";
import { SeguridadService } from "./seguridad.service";
@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    constructor(private seguridadService: SeguridadService) { }
    intercept(req: HttpRequest<any>, next: HttpHandler) {
        const authToken = this.seguridadService.getToken();
        if(authToken!==null){
            req = req.clone({
                setHeaders: {
                    Authorization: "Bearer " + authToken
                }
            });
        }
        return next.handle(req);
    }
}