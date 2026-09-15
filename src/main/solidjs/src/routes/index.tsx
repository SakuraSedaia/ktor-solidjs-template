import { Title } from '@solidjs/meta';
import Counter from '~/components/Counter';
import logo from '~/logo.svg';
import {createMemo, Loading} from "solid-js";
import type { KtorHello } from "~/components/types";
import {asyncFetch} from "~/utils/asyncUtils";

export default function Home() {
  const ktor = createMemo(async (): Promise<KtorHello> => asyncFetch<KtorHello>({
    apiRoute: "hello"
  }));

  return (
    <main>
      <Title>Home - Solid App</Title>
      <img src={logo} class="logo" alt="Solid logo" />
      <h1>Hello Solid + <Loading fallback={"loading..."}>{ktor().hello}!</Loading></h1>
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
