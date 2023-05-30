import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class PedidosService {
  
  private MS_PEDIDOS_URL = environment.MS_PEDIDOS_URL;
  private MS_PAGOS_URL = environment.MS_PAGOS_URL;


  constructor(private http: HttpClient) { }

  realizarPedido(pedidoDTO:any){
    let api = `${this.MS_PEDIDOS_URL}/Pedido/realizarPedido`;
    return this.http.post(api,pedidoDTO);
  }
  realizarPago(){
    let api = `${this.MS_PAGOS_URL}/Pagos/pagar`;
    return this.http.get(api);
  }
}
