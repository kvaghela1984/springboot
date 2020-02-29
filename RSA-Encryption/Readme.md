**1. openssl genrsa -out rsakey.pem 2048**

**2. openssl rsa -pubout -inform pem -in rsakey.pem -outform DER -out public.der**

**3. openssl rsa -pubout -inform pem -in rsakey.pem -outform PEM -out public.pem**

**4. openssl pkcs8 -topk8 -nocrypt -inform PEM -in rsakey.pem -outform DER -out private.der**

**5. openssl pkcs8 -topk8 -nocrypt -inform PEM -in rsakey.pem -outform PEM -out private.pem**

