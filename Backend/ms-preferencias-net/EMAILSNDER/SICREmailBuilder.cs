using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ms_preferencias_net.DAL.Entidades;

namespace ms_preferencias_net.EMAILSNDER
{
    public class SICREmailBuilder
    {
        public static string BuildPromotionsEmail(List<Producto> productos)
        {
            StringBuilder sb = new StringBuilder();

            sb.AppendLine("¡Celebra con nosotros y aprovecha nuestras promociones especiales!");
            sb.AppendLine();
            sb.AppendLine("1. Menú del Día: Disfruta de nuestro delicioso menú del día con una variedad de platos exquisitos a un precio increíble. ¡Una opción perfecta para tus almuerzos!");
            sb.AppendLine();
            sb.AppendLine("2. Happy Hour: Únete a nuestro Happy Hour y disfruta de bebidas refrescantes y cócteles únicos a precios promocionales. ¡Relájate y disfruta de un ambiente agradable!");
            sb.AppendLine();
            sb.AppendLine("3. Noches Temáticas: Déjate llevar por nuestras Noches Temáticas, donde podrás disfrutar de la gastronomía de diferentes regiones del mundo. Una experiencia culinaria inolvidable.");
            sb.AppendLine();
            sb.AppendLine("4. Descuento Familiar: Ven en familia y obtén un descuento especial. Sabemos lo importante que es compartir momentos especiales con tus seres queridos, por eso queremos consentirte.");
            sb.AppendLine();
            sb.AppendLine("5. Menú Degustación: Descubre nuestra selección de platos gourmet con nuestro Menú Degustación. Una experiencia culinaria única que despertará tus sentidos.");
            sb.AppendLine();
            sb.AppendLine("6. Tarjeta de Fidelidad: Únete a nuestro programa de fidelidad y acumula puntos con cada visita. Canjea tus puntos por descuentos y beneficios exclusivos.");
            sb.AppendLine();
            sb.AppendLine("7. Eventos Privados: ¿Tienes un evento especial? Nuestro restaurante ofrece espacios privados para celebraciones, reuniones de negocios y ocasiones especiales. Déjanos hacer de tu evento una experiencia inolvidable.");
            sb.AppendLine();
            sb.AppendLine("¡No pierdas la oportunidad de disfrutar de estas promociones! Reserva tu mesa ahora mismo y déjanos consentirte con nuestra ");
            sb.AppendLine("deliciosa comida y atención de primera calidad. Te esperamos con los brazos abiertos.");
            sb.AppendLine();
            sb.AppendLine("Contamos con los siguientes productos en nuestros diferentes restaurantes que pueden ser de tu interés: ");
            sb.AppendLine();

            foreach (var producto in productos)
            {
                sb.AppendLine(" - " + producto.Nombre);
                sb.AppendLine();
            }
            sb.AppendLine("¡Te esperamos!");
            return sb.ToString();
        }
    }
}
