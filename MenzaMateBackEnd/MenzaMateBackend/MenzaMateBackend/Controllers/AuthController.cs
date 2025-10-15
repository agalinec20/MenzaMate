using MenzaMate.Business.Models.ModelsUser;
using MenzaMate.Business.Services.INameService;
using MenzaMate.Business.Services.ServicesUser;
using Microsoft.AspNetCore.Mvc;

namespace MenzaMateBackend.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class AuthController : ControllerBase
    {
        private readonly IAuthService _authService;
        private readonly IUserService _userService;
        public AuthController(IAuthService authService, IUserService userService)
        {
            _authService = authService;
            _userService = userService;
        }
        [HttpPost("google-login")]
        public async Task<IActionResult> GoogleLogin([FromBody] TokenRequest tokenRequest)
        {
            try
            {
                var googleUser = await _authService.GoogleLoginAsync(tokenRequest.IdToken);

                if (googleUser == null)
                {
                    return Unauthorized("Invalid token");
                }

                var googleUserDto = new GoogleUserDto
                {
                    Name = googleUser.Username,
                    Email = googleUser.Email,
                    GoogleId = googleUser.GoogleId,
                };

                var userDto = await _userService.AddOrGetUserAsync(googleUserDto);

                return Ok(userDto);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

    }
}
