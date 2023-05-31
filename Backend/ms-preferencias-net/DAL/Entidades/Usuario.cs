using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ms_preferencias_net.Entidades
{
    public class Usuario
    {
        public int Id { get; set; }
        public int TipoUsuarioId { get; set; }
        public string Username { get; set; }
        public string Nombres { get; set; }
        public string Apellidos { get; set; }
        public string Email { get; set; }
    }
}
