import { Title } from '@solidjs/meta';
import Counter from '~/components/Counter';
import logo from '~/logo.svg';
import {createMemo, Loading} from "solid-js";
import type { KtorHello } from "~/components/types";

// TODO: Rewrite this page to make clear the Ktor Integration
export default function Home() {
  const ktor = createMemo(async (): Promise<string> => {
    const res = await fetch("/api/v1/hello")

    if (!res.ok) throw new Error(`Request failed: ${res.status}`)

    const data: KtorHello = await res.json()
    return data.hello
  });

  return (
    <main>
      <Title>Home - Solid App</Title>
      <img src={logo} class="logo" alt="Solid logo" />
      <h1>Hello Solid + <Loading fallback={"loading..."}>{ktor()}!</Loading></h1>
      <Counter />
      <p>
        Edit <code>src/routes/index.tsx</code> and save to reload.
      </p>
      <a
        href="https://v2.solidjs.com/"
        target="_blank"
        rel="noopener noreferrer"
      >
        Learn Solid
      </a>
    </main>
  );
}
