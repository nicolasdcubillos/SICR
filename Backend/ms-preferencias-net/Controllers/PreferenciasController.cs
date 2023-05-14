using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace ms_preferencias_net.Controllers
{
    [Route("[controller]")]
    [ApiController]
    public class PreferenciasController : ControllerBase
    {
        [HttpGet("getPreferencias")]
        public async Task<ActionResult<List<String>>> Get()
        {
            List<String> respuestaPrueba = new List<string>();
            respuestaPrueba.Add("Lista de preferencias para el usuario!");
            return respuestaPrueba;
        }
    }
}
