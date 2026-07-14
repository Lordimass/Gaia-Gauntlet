import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  /* config options here */
  reactCompiler: true,
  distDir: 'out',
  output: 'export',
  images: {
    unoptimized: true,
    remotePatterns: [new URL("https://*.twimg.com/**")]
  }
};

export default nextConfig;
