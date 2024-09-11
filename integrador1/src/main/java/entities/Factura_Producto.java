package entities;

public class Factura_Producto {

        private int idProducto;
        private int idFactura;
        private int cantidad;


        public Factura_Producto(int id_Factura, int idProducto, int cantidad) {
            this.idFactura = id_Factura;
            this.idProducto = idProducto;
            this.cantidad = cantidad;
        }

        public int getIdFactura() {
            return idFactura;
        }

        public void setId_Factura(int id_Factura) {
            this.idFactura = id_Factura;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }

        public int getIdProducto() {
            return idProducto;
        }

        public void setIdProducto(int idProducto) {
            this.idProducto = idProducto;
        }


        @Override

        public String toString() {
            return "Factura_Producto{" +"idProducto="+idProducto+ "idFactura=" + idFactura + ", cantidad=" + cantidad + '}';
        }
    }

