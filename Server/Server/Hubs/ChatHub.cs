using Microsoft.AspNetCore.SignalR;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Hubs
{
    public class ChatHub : Hub
    {
        public async Task MessageFromServer(string message)
        {
            Console.WriteLine("Message received: " + message);
            await Clients.Others.SendAsync("ReceiveNewMessage", message);
        }
    }
}
