using MenzaMate.Business.Services.INameService;
using MenzaMate.Data.Entities;
using MenzaMate.Data.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MenzaMate.Business.Services.ServicesAuth
{
    public class AuthService : IAuthService
    {
        private readonly IGoogleAuthService _googleAuthService;

        public AuthService(IGoogleAuthService googleAuthService)
        {
            _googleAuthService = googleAuthService;
        }

        public async Task<User> GoogleLoginAsync(string idToken)
        {
            var googleUserPayload = await _googleAuthService.ValidateTokenAsync(idToken);

            if (googleUserPayload == null)
                return null;

            var user = new User
            {
                GoogleId = googleUserPayload.GoogleId,
                Email = googleUserPayload.Email,
                Username = googleUserPayload.Name,  
                Role = RolesEnum.Student.ToString() 
            };

            return user;
        }

    }
}
