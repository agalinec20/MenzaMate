using Google.Apis.Auth;
using MenzaMate.Business.Models.ModelsUser;
using MenzaMate.Business.Services.INameService;
using Microsoft.Extensions.Configuration;

namespace MenzaMate.Business.Services.ServicesAuth
{
    public class GoogleAuthService : IGoogleAuthService
    {
        private readonly string _clientId;

        public GoogleAuthService(IConfiguration configuration)
        {
            _clientId = configuration["GoogleAuth:ClientId"];
        }

        public async Task<GoogleUserPayload?> ValidateTokenAsync(string idToken)
        {
            try
            {
                var payload = await GoogleJsonWebSignature.ValidateAsync(idToken, new GoogleJsonWebSignature.ValidationSettings
                {
                    Audience = new[] { _clientId }
                });


                return new GoogleUserPayload
                {
                    GoogleId = payload.Subject,
                    Name = payload.Name,
                    Email = payload.Email,
                    PictureUrl = payload.Picture
                };
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error validating ID token: {ex.Message}");
                return null;
            }
        }
    }

}
