using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
 
namespace WebDownloader
{
    class Program
    {
        private static List<string> pages = new List<string>();
        static void Main(string[] args)
        {
            pages.Add(@"https://stackoverflow.com/questions/42077388/type-used-in-a-using-statement-must-be-implicitly-convertible-to-system-idispos");
            pages.Add(@"https://stackoverflow.com/questions/12931687/targetinvocationexception-occurred-but-i-have-absolutely-no-idea-why");
            pages.Add(@"https://stackoverflow.com/questions/3458046/how-to-include-quotes-in-a-string");
            for (int pos = 0; pos < pages.Count; pos++)
            {
                string page = pages.ElementAt(pos);
                Task.Run(() => DownloadPageEvent(page));
            }
            Console.ReadLine();
        }

        static async void DownloadPageAsync(string page)
        {
            HttpClient client = new HttpClient();
            HttpResponseMessage response = await client.GetAsync(page);
            HttpContent content = response.Content;
            string result = await content.ReadAsStringAsync();
            Console.WriteLine(result);
            long len = (long)response.Content.Headers.ContentLength;
            Console.WriteLine("Length of page: " + page +" is " + len);
        }

        static void DownloadPageEvent(string page)
        {
            using (WebClient client = new WebClient())
            {
                client.DownloadStringCompleted += (sender, e) =>
                {
                    Console.WriteLine(e.Result);
                };
                client.DownloadStringAsync(new Uri(page));
            }

        }
    }
}