using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using ms_preferencias_net.DAL.Entidades;
using ms_preferencias_net.Entidades;

namespace ms_preferencias_net.Context
{

    public class RDSDbContext : DbContext
    {
        const string connectionString = "Server=database-sicr.cbtbx79jvpr1.us-east-2.rds.amazonaws.com; User ID=admin; Password=password123*; Database=sicr_db";
        public DbSet<Usuario> Usuario { get; set; }
        public DbSet<Producto> Producto { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseMySql(connectionString, ServerVersion.AutoDetect(connectionString));
        }


    }
}
