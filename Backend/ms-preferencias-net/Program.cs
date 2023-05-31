using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Mail;
using System.Threading;
using ms_preferencias_net.Context;
using ms_preferencias_net.DAL.Entidades;
using ms_preferencias_net.EMAILSNDER;
using ms_preferencias_net.Entidades;

class Program
{
    static void Main(string[] args)
    {
        while (true)
        {
            using (var contexto = new RDSDbContext())
            {
                List<Usuario> usuarios = contexto.Usuario.ToList();
                List<Producto> productos = contexto.Producto.ToList();

                foreach (var usuario in usuarios)
                {
                    string senderEmail = "nicolasdavidcubillos@gmail.com";
                    string senderPassword = "pkygberzwfwfhesg";

                    string subject = "SICR | Promociones";
                    string body = SICREmailBuilder.BuildPromotionsEmail(productos);

                    try
                    {
                        MailMessage mail = new MailMessage(senderEmail, usuario.Email, subject, body);
                        SmtpClient smtpClient = new SmtpClient("smtp.gmail.com", 587);

                        smtpClient.EnableSsl = true;
                        smtpClient.UseDefaultCredentials = false;
                        smtpClient.Credentials = new NetworkCredential(senderEmail, senderPassword);

                        smtpClient.Send(mail);

                        Console.WriteLine("Correo electrónico enviado correctamente al cliente " + usuario.Email);
                    }
                    catch (Exception ex)
                    {
                        Console.WriteLine("Error al enviar el correo electrónico: " + ex.Message);
                    }
                }
            }

            Thread.Sleep(TimeSpan.FromMinutes(15));
        }
    }
}
