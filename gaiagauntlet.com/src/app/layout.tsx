import type {Metadata} from "next";
import "./globals.css";
import React from "react";
import localFont from "next/font/local"

export const metadata: Metadata = {
    title: "Gaia Gauntlet",
    description: "One event. Countless creators. One champion. 👑",
};

const ari_w9500 = localFont({
  src: [
    {
      path: "./fonts/ari-w9500.ttf",
      weight: "normal",
      style: "normal"
    },
    {
      path: "./fonts/ari-w9500-display.ttf",
      weight: "bold",
      style: "normal"
    }
  ]
})

export default function RootLayout({
                                       children,
                                   }: Readonly<{
    children: React.ReactNode;
}>) {
    return (
        <html lang="en" data-bs-theme="dark">
        <body>{children}</body>
        </html>
    );
}
